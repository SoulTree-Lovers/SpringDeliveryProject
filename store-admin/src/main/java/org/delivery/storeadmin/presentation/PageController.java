package org.delivery.storeadmin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class PageController {

    @RequestMapping(path = {"/","/main"})
    public ModelAndView main() {
        return new ModelAndView("main"); // main.html 파일과 매핑
    }

    @RequestMapping(path = "/order")
    public ModelAndView order() {
        return new ModelAndView("order/order"); // order 디렉토리 안에 있는 order.html과 매핑
    }
}
