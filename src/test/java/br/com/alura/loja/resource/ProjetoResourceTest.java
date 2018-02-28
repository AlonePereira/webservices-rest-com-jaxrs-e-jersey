package br.com.alura.loja.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;

import junit.framework.Assert;

public class ProjetoResourceTest {

    @Test
    public void testaConexacaoComServidor() {
	Client client = ClientBuilder.newClient();
	WebTarget target = client.target("http://localhost:8080");
	String conteudo = target.path("/projetos").request().get(String.class);
	
	Assert.assertTrue(conteudo.contains("<nome>Minha loja"));
    }
    
}
