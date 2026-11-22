package br.edu.exemplo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
@SpringBootTest @AutoConfigureMockMvc @Transactional
class ProdutoControllerIntegrationTest {
 @Autowired MockMvc mockMvc;
 @Test void deveCadastrarProdutoPelaApi() throws Exception {
  String json = """
        {
            "nome": "Mouse",
            "preco": 80.0
        }
        """;

  mockMvc.perform(post("/produtos")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(json))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").isNumber())
          .andExpect(jsonPath("$.nome").value("Mouse"))
          .andExpect(jsonPath("$.preco").value(80.0));

 }
 @Test void deveBuscarProdutoPelaApi() throws Exception {
  String json = """
        {
            "nome": "Mouse",
            "preco": 80.0
        }
        """;

  String resposta = mockMvc.perform(post("/produtos")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(json))
          .andExpect(status().isOk())
          .andReturn()
          .getResponse()
          .getContentAsString();

  ObjectMapper mapper = new ObjectMapper();

  JsonNode produtoJson = mapper.readTree(resposta);
  long id = produtoJson.get("id").asLong();

  mockMvc.perform(get("/produtos/{id}", id))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(id))
          .andExpect(jsonPath("$.nome").value("Mouse"))
          .andExpect(jsonPath("$.preco").value(80.0));
 }
}
