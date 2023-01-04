package com.example2.demo2;


import com.example2.demo2.model.TodoItem;
import com.example2.demo2.repo.TodoRepo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration

public class MyStepdefs extends SpringIntegrationTest{

    @MockBean
    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private TestRestTemplate restTemplate;

    private String caller; // input

    private String excep;
    private ResponseEntity<String> response; // output

    @Given("I populate tasks")
    public void iPopulateTasks() {

        todoRepo.save(new TodoItem(1L, "play football", false, null));
        todoRepo.save(new TodoItem(2L, "play cricket", false, null));

    }

    @When("I fire a GET request to fetch all tasks")
    public void iFireAGETRequestToFetchAllTasks() {


        response = restTemplate.exchange("http://localhost:8080/todo", HttpMethod.GET, null, String.class);
    }

    @Then("I should get a response with HTTP status code {int}")
    public void iShouldGetAResponseWithHTTPStatusCodeStatus(int sts) {
        assertThat(response.getStatusCodeValue()).isEqualTo(sts);
    }

    @Given("I Delete Task")
    public void iDeleteTask() {
        todoRepo.deleteById(1L);
    }

    @When("I Delete a already deleted task")
    public void iDeleteAAlreadyDeletedTask() {

        try {

            todoRepo.deleteById(1L);

        } catch (Exception e){
            excep=e.toString();
        }
    }


    @Then("I should get a response an exception {string}")
    public void iShouldGetAResponseAnExceptionStatuss(String excp) {

            assertThat(excep.equals(excp));

    }



}