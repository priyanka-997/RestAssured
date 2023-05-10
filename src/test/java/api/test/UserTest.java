package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.UserPojo;
import io.restassured.response.Response;

public class UserTest {
	
	Faker faker;
	UserPojo userPayload;
	
	@BeforeClass()
	public void setupData() {
		
		faker = new Faker();
		userPayload= new UserPojo();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setUsername(faker.name().username());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());		
		
	}
	
	@Test(priority=1)
	public void testPostUser() {
		
	Response response= UserEndPoints.createUser(userPayload);
	response.then().log().all();
	
	Assert.assertEquals(response.getStatusCode(), 200);
		
		
	}
	
	
	@Test(priority=2)
	public void testGetUser() {
		
		Response response=	UserEndPoints.getUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
			
			
		}
	
	@Test(priority=3)
	public void testPutUser() {
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		
		Response response= UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
		Response responseAfterUpdate=	UserEndPoints.getUser(this.userPayload.getUsername());		
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
			
			
		}
	
	@Test(priority=4)
	public void testDeleteUser() {
		
		Response response= UserEndPoints.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
			
			
		}

}
