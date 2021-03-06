package br.com.alura.loja.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.loja.Servidor;
import br.com.alura.loja.modelo.Projeto;
import junit.framework.Assert;

public class ProjetoResourceTest {

    private HttpServer servidor;
    private Client client;

    @Before
    public void startarServidor() {
	servidor = Servidor.startarServidor();
	ClientConfig config = new ClientConfig();
	config.register(new LoggingFilter());
	client = ClientBuilder.newClient(config);
    }
    
    @After
    public void pararServidor() {
	servidor.stop();
	client.close();
    }
    
    @Test
    public void testaConexacaoComServidor() {
	WebTarget target = client.target("http://localhost:8080");
	Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
	
	Assert.assertEquals("Minha loja", projeto.getNome());
    }
    
    @Test
    public void testaAdicionarProjeto() {
	WebTarget target = client.target("http://localhost:8080");
	
	Projeto projeto = new Projeto(3l, "PlasUtil", 2017);
	
	Entity<Projeto> entity = Entity.entity(projeto, MediaType.APPLICATION_XML);
	Response response = target.path("/projetos").request().post(entity);
	
	Assert.assertEquals(201, response.getStatus());
	
	String location = response.getHeaderString("Location");
	Projeto projetoSalvo = client.target(location).request().get(Projeto.class);
	Assert.assertEquals("PlasUtil", projetoSalvo.getNome());
    }
    
}
