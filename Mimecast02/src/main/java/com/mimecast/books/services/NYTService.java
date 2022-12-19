package com.mimecast.books.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimecast.books.model.Book;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@ConditionalOnProperty(prefix = "book", name = "service", havingValue = "nyt")
public class NYTService implements BooksService {
	private final WebClient webClient;
	private final String apiKey;

	public NYTService(WebClient.Builder webClientBuilder,
	                  @Value("${nytservice.apikey}") String appKey,
	                  @Value("${nytservice.baseurl}") String baseURL) {
		this.apiKey = appKey;

		HttpClient httpClient = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				.responseTimeout(Duration.ofMillis(5000))
				.doOnConnected(conn ->
						conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
								.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

		this.webClient = webClientBuilder
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.baseUrl(baseURL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}


	public List<Book> getByAuthor(String author) {
		return this.webClient.get()
				.uri("/reviews.json?api-key={appKey}&author={author}", apiKey, author)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(JsonNode.class)
//				.log()
				.map(s -> s.path("results"))
				.map(s -> {
					try {
						ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
						return objectMapper.readValue(s.traverse(), new TypeReference<>() {
						});
					} catch(IOException e) {
						e.printStackTrace();//TODO Should be handled in more proper way.
						return Collections.<Book>emptyList();
					}
				})
				.block(Duration.ofSeconds(10));
	}
}
