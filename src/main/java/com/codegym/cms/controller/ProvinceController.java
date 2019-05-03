package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.service.CustomerService;
import com.codegym.cms.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/province")
    public ModelAndView listProvince() {
        ModelAndView provinceListMAV = new ModelAndView("/province/list_province");
        Iterable<Province> listProvince = provinceService.findAll();
        provinceListMAV.addObject("provinceList", listProvince);
        return provinceListMAV;
    }

    @GetMapping("/create-province")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("/province/create-province");
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }

    @PostMapping("/create-province")
    public ModelAndView create(@ModelAttribute("province") Province province) {
        provinceService.save(province);

        ModelAndView modelAndView = new ModelAndView("/province/create-province");
        modelAndView.addObject("province", new Province());
        modelAndView.addObject("message", "New province created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-province/{id}")
    public ModelAndView editProvince(@PathVariable("id") Long id){
        ModelAndView modelAndView;
        Province province=provinceService.findById(id);
        if (province!=null){
            modelAndView=new ModelAndView("/province/edit-province");
            modelAndView.addObject("province", new Province());
            return modelAndView;
        }
        else {
            modelAndView=new ModelAndView("error-404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-province")
    public ModelAndView updateProvince(@ModelAttribute("province") Province province){
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("/province/edit-province");
        modelAndView.addObject("province", province);
        return modelAndView;
    }

    @GetMapping("/view-province/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id){
        Province province=provinceService.findById(id);
        ModelAndView modelAndView;
        if(province!=null){
            Iterable<Customer> customers=customerService.findByProvince(province);
            modelAndView=new ModelAndView("/province/view");
            modelAndView.addObject("province",province);
            modelAndView.addObject("customers",customers);
            return modelAndView;
        }
        else {
            modelAndView=new ModelAndView("/province/error-404");
            return modelAndView;
        }
    }

}
