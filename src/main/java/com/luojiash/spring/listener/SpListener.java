package com.luojiash.spring.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class SpListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event);
        // org.springframework.context.event.ContextRefreshedEvent[source=Root WebApplicationContext: startup date [Sat Aug 20 16:19:30 CST 2016]; root of context hierarchy]
        // org.springframework.context.event.ContextRefreshedEvent[source=WebApplicationContext for namespace 'dispatcherServlet-servlet': startup date [Sat Aug 20 16:19:34 CST 2016]; parent: Root WebApplicationContext]
        // ServletRequestHandledEvent: url=[/]; client=[127.0.0.1]; method=[GET]; servlet=[dispatcherServlet]; session=[null]; user=[null]; time=[16ms]; status=[OK]
    }

}
