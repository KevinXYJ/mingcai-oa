/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mingcai.edu.modules.oa.web.proc;

import com.mingcai.edu.common.config.Global;
import com.mingcai.edu.common.persistence.Page;
import com.mingcai.edu.common.utils.StringUtils;
import com.mingcai.edu.common.web.BaseController;
import com.mingcai.edu.modules.oa.entity.eos.OaEosPro;
import com.mingcai.edu.modules.oa.entity.eos.OaEosProStart;
import com.mingcai.edu.modules.oa.entity.eos.OaEosProStartItem;
import com.mingcai.edu.modules.oa.entity.proc.OaProcApplylist;
import com.mingcai.edu.modules.oa.service.eos.OaEosProService;
import com.mingcai.edu.modules.oa.service.eos.OaEosProStartItemService;
import com.mingcai.edu.modules.oa.service.eos.OaEosProStartService;
import com.mingcai.edu.modules.oa.service.proc.OaProcApplylistService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 采购申请单Controller
 * @author 谢一郡
 * @version 2019-05-06
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/proc/oaProcApplylist")
public class OaProcApplylistController extends BaseController {

	@Autowired
	private OaProcApplylistService oaProcApplylistService;
	@Autowired
	private OaEosProService oaEosProService;
	@Autowired
	private OaEosProStartItemService oaEosProStartItemService;
	@ModelAttribute
	public OaProcApplylist get(@RequestParam(required=false) String id) {
		OaProcApplylist entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaProcApplylistService.get(id);
		}
		if (entity == null){
			entity = new OaProcApplylist();
			//初始化当前日期
			Date currentDate =new Date();
			entity.setUpdateDate(currentDate);
		}
		return entity;
	}
	
	@RequiresPermissions("oa:proc:oaProcApplylist:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaProcApplylist oaProcApplylist, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaProcApplylist> page = oaProcApplylistService.findPage(new Page<OaProcApplylist>(request, response), oaProcApplylist); 
		model.addAttribute("page", page);
		return "modules/oa/proc/oaProcApplylistList";
	}


	@RequiresPermissions("oa:proc:oaProcApplylist:view")
	@RequestMapping(value = "form")
	public String form(OaProcApplylist oaProcApplylist,Model model) {
		model.addAttribute("oaProcApplylist", oaProcApplylist);
		List<OaEosPro> list=oaEosProService.findEosPro(new OaEosPro());
		model.addAttribute("eosProlist", list);
		OaEosProStartItem item=new OaEosProStartItem();
		if(StringUtils.isNotEmpty(oaProcApplylist.getProId())){
			item.setEosId(oaProcApplylist.getProId());
		}
		List<OaEosProStartItem> page = oaEosProStartItemService.findList(item);
		model.addAttribute("page",page);
		return "modules/oa/proc/oaProcApplylistForm";
	}

	@RequiresPermissions("oa:proc:oaProcApplylist:edit")
	@RequestMapping(value = "save")
	public String save(OaProcApplylist oaProcApplylist, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaProcApplylist)){
			return form(oaProcApplylist, model);
		}
		oaProcApplylistService.save(oaProcApplylist);
		addMessage(redirectAttributes, "保存采购申请单成功！");
		return "redirect:"+Global.getAdminPath()+"/oa/proc/oaProcApplylist/?repage";
	}

	
	@RequiresPermissions("oa:proc:oaProcApplylist:edit")
	@RequestMapping(value = "delete")
	public String delete(OaProcApplylist oaProcApplylist, RedirectAttributes redirectAttributes) {
		oaProcApplylistService.delete(oaProcApplylist);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/oa/proc/oaProcApplylist/?repage";
	}

}