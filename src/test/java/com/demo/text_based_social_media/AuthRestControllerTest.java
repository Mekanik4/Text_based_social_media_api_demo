//package com.demo.text_based_social_media;
//
//import com.demo.text_based_social_media.api.user.authentication.adapter.in.AuthRestController;
//import com.demo.text_based_social_media.api.user.authentication.application.port.in.LoginUseCase;
//import com.demo.text_based_social_media.api.user.authentication.application.port.in.SignUpUseCase;
//import com.demo.text_based_social_media.api.user.authentication.domain.SignUpRequest;
//import com.demo.text_based_social_media.mapper.UserMapper;
//import com.demo.text_based_social_media.repository.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(AuthRestController.class)
//public class AuthRestControllerTest {
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserMapper userMapper;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private SignUpUseCase signUpUseCase;
//
//    @MockBean
//    private LoginUseCase loginUseCase;
//
////    @BeforeEach
////    void setUp() {
////        userRepository = mock(UserRepository.class);
////        userMapper = mock(UserMapper.class);
////    }
//
//    @Test
//    void shouldCreateUser() throws Exception {
//        SignUpRequest user = new SignUpRequest("testuser@demo.com", "12345678", "USER", false);
//
//        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isCreated())
//                .andDo(print());
//    }
//
//    @Test
//    void shouldLoginUser() throws Exception {
//
//    }
//}
