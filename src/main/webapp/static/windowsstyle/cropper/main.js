$(function () {
  'use strict';
  var console = window.console || { log: function () {} };
  var URL = window.URL || window.webkitURL;
  $('#image').attr("src",parent.getImage());
  var $image = $('#image');
  var options = {
        aspectRatio: 1 / 1,
        preview: '.img-preview',
        crop: function (e) {
        }
      };
  var originalImageURL = $image.attr('src');
  var uploadedImageType = 'image/jpeg';
  var uploadedImageURL;
  // Tooltip
  $('[data-toggle="tooltip"]').tooltip();
  // Cropper
  $image.on({
    ready: function (e) {
      console.log(e.type);
    },
    cropstart: function (e) {
      console.log(e.type, e.action);
    },
    cropmove: function (e) {
      console.log(e.type, e.action);
    },
    cropend: function (e) {
      console.log(e.type, e.action);
    },
    crop: function (e) {
      console.log(e.type, e.x, e.y, e.width, e.height, e.rotate, e.scaleX, e.scaleY);
    },
    zoom: function (e) {
      console.log(e.type, e.ratio);
    }
  }).cropper(options);
  // Buttons
  if (!$.isFunction(document.createElement('canvas').getContext)) {
    $('button[data-method="getCroppedCanvas"]').prop('disabled', true);
  }

  if (typeof document.createElement('cropper').style.transition === 'undefined') {
    $('button[data-method="rotate"]').prop('disabled', true);
    $('button[data-method="scale"]').prop('disabled', true);
  }
  // Methods
  $('#Images').click(function(){
    var $this = $(this);
    var data = $this.data();
    var cropper = $image.data('cropper');
    var cropped;
    var $target;
    var result;
    if ($this.prop('disabled') || $this.hasClass('disabled')) {
      return;
    }
    if (cropper && data.method) {
      data = $.extend({}, data); // Clone a new one
      if (typeof data.target !== 'undefined') {
        $target = $(data.target);
        if (typeof data.option === 'undefined') {
          try {
            data.option = JSON.parse($target.val());
          } catch (e) {
            console.log(e.message);
          }
        }
      }
      cropped = cropper.cropped;
      if (uploadedImageType === 'image/jpeg') {
        if (!data.option) {
          data.option = {};
        }
        data.option.fillColor = '#fff';
      }
      result = $image.cropper(data.method, data.option, data.secondOption);
        if (result) {
            // Bootstrap's Modal
            //$('#getCroppedCanvasModal').modal().find('.modal-body').html(result);
            $("input[name='images']").val(result.toDataURL(uploadedImageType));
        }
        if ($.isPlainObject(result) && $target) {
        try {
            $("input[name='images']").val(JSON.stringify(result));
        } catch (e) {
          console.log(e.message);
        }
      }

    }
  });

  // Keyboard
  $(document.body).on('keydown', function (e) {
    if (!$image.data('cropper') || this.scrollTop > 300) {
      return;
    }
    switch (e.which) {
      case 37:
        e.preventDefault();
        $image.cropper('move', -1, 0);
        break;
      case 38:
        e.preventDefault();
        $image.cropper('move', 0, -1);
        break;

      case 39:
        e.preventDefault();
        $image.cropper('move', 1, 0);
        break;

      case 40:
        e.preventDefault();
        $image.cropper('move', 0, 1);
        break;
    }
  });
  // Import image
  var $inputImage = $('#inputImage');
  if (URL) {
    $inputImage.change(function () {
      var files = this.files;
      var file;
      if (!$image.data('cropper')) {
        return;
      }
      if (files && files.length) {
        file = files[0];
        if (/^image\/\w+$/.test(file.type)) {
          uploadedImageType = file.type;
          if (uploadedImageURL) {
            URL.revokeObjectURL(uploadedImageURL);
          }
          uploadedImageURL = URL.createObjectURL(file);
          $image.cropper('destroy').attr('src', uploadedImageURL).cropper(options);
          $inputImage.val('');
        } else {
          window.alert('Please choose an image file.');
        }
      }
    });
  } else {
      $inputImage.prop('disabled', true).parent().addClass('disabled');
  }

});
