package com.mondari;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class I18nApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("测试英文国际化")
    @Test
    void testLocaleEnglish() throws Exception {
        this.mockMvc.perform(get("/").locale(Locale.US))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.MessageSource.inquiry").value("How are you?"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.MessageSource.greetings").value("Hello.boys and girls"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.MessageSource.farewell").value("Goodbye."))
        ;
    }

    @DisplayName("测试中文国际化")
    @Test
    void testLocaleChinese() throws Exception {
        this.mockMvc.perform(get("/").locale(Locale.CHINA))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ResourceBundle.inquiry").value("最近过得怎样？"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ResourceBundle.greetings").value("你好.boys and girls"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ResourceBundle.farewell").value("再见."))
        ;
    }

}
