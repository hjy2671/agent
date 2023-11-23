package org.nature.shadow;

import org.nature.net.annotation.Post;
import org.nature.net.annotation.Solution;


@Solution("/test")
public class TestTarget {

    @Post("/run")
    public String sun(String name, int age) {
        System.out.println(name + " " + age);
        return name;
    }

    @Post("/say")
    public String moon(TestBean bean, int age) {
        System.out.println("say=>>> name=" + bean.getName() + " " + "age=" + bean.getAge() + "  ".concat(age+""));
        return "success to visit moon method ".concat(bean.getName()).concat(" ").concat(bean.getAge()+"");
    }

}
