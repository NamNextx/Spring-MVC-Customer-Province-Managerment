package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.service.CustomerService;
import com.codegym.cms.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    @GetMapping("/create-customer")
    public ModelAndView showCustomerForm(){
        ModelAndView modelAndView=new ModelAndView("/customer/create");
        modelAndView.addObject("customer",new Customer());
        return modelAndView;
    }
    @PostMapping("/create-customer")
    public String saveCustomer( Customer customer, RedirectAttributes redirect){
        customerService.save(customer);
        redirect.addFlashAttribute("messages","customer created");
        return ("redirect:/customers");

    }

    @GetMapping("/customers")
    public ModelAndView showListCustomer(){
        ModelAndView modelAndViewListCustomer=new ModelAndView("/customer/showListCustomer");
        Iterable<Customer> customerList=customerService.findAll();
        modelAndViewListCustomer.addObject("customerList",customerList);
        return modelAndViewListCustomer;
    }

    @GetMapping("/edit-customer/{id}")
    public String editCustomer(@PathVariable Long id, Model model){
        model.addAttribute("customer",customerService.findById(id));
        return "/customer/edit";
    }
    @PostMapping("/customer-update")
    public String update(@ModelAttribute("customer") Customer customer, RedirectAttributes redirect){
        customerService.save(customer);
        redirect.addFlashAttribute("messages","Customer updated");
        return ("redirect:/customers");
    }
    @GetMapping("/delete-customer/{id}")
    public ModelAndView deleteCustomerGet(@PathVariable Long id){
        Customer customer=customerService.findById(id);
        if (customer !=null){
            ModelAndView modelAndView=new ModelAndView("/customer/delete_form");
            modelAndView.addObject("customer",customer);
            return modelAndView;
        }else {
            ModelAndView modelAndView=new ModelAndView("error-404");
            return modelAndView;
        }
    }
    @PostMapping("/delete_customer")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer ){
        customerService.remove(customer.getId());
        return ("redirect:customers");
    }
}
