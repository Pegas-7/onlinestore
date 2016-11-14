package com.yauhenikuntsevich.training.onlinestore.daoxml;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;

@Repository
public abstract class AbstractEntityDaoXml {
	protected XStream xstream;
	protected File file;

	@Value("${basePath}")
	protected String basePath;

	@PostConstruct
	private void intialize() throws IOException {
		xstream = new XStream();
	}
}
