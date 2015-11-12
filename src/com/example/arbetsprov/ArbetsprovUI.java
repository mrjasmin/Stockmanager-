package com.example.arbetsprov;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.annotation.WebServlet;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import com.example.arbetsprov.Bicycle;


@SuppressWarnings("serial")
@Theme("arbetsprov")
public class ArbetsprovUI extends UI {

	TextField search = new TextField(); 
	Label label = new Label("Artikelregister"); 
	
	Button newArticleButton = new Button("Ny Artikel"); 

	ArticleForm articleForm = new ArticleForm(); 

	public JPAContainer<Bicycle> bicycles; 
	
	public Table articles = new Table();
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ArbetsprovUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		
		bicycles = JPAContainerFactory.make(Bicycle.class, "bicyclesPU"); 
		
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
	
		configureComponents(); 
		buildGUI(); 
	
	}
	
	private void buildGUI(){
		
VerticalLayout main = new VerticalLayout(); 
		
		VerticalLayout header = new VerticalLayout(); 
		header.addStyleName("header");
		header.addComponent(label); 
		
		main.addComponent(header);
		
		main.addComponent(search);
		main.addComponent(articles);
		main.addComponent(newArticleButton);
		main.addComponent(articleForm); 
		
		setContent(main); 
		
	}
	
	private void configureComponents(){
		label.addStyleName("header2");
		
		search.setInputPrompt("Sök Artikel.....");
		search.setStyleName("search_button"); 
		search.setWidth("42%");
		
		search.addTextChangeListener(e -> filterContacts(e.getText())); 
		
		newArticleButton.addClickListener(e -> articleForm.showForm()); 
		
		newArticleButton.addStyleName("add_article_button"); 
		newArticleButton.setWidth("42%");
		
		articles.setSelectable(true);
		
		articles.addGeneratedColumn("image", new ColumnGenerator() {
			@Override
			public Component generateCell(Table table, Object itemId, Object columnId) {

				Component component;

				EntityItem<Bicycle> bicycleEntity;

				bicycleEntity = bicycles.getItem(itemId);

				final byte[] img = bicycleEntity.getEntity().getImage();

				String name = bicycleEntity.getEntity().getArticleID();

				if (img == null) {
					component = new Label("No image to show");
				} else {
					StreamResource.StreamSource imageSource = new StreamResource.StreamSource() {
						@Override
						public InputStream getStream() {
							return new ByteArrayInputStream(img);
						}
					};
					StreamResource imageResource = new StreamResource(imageSource, name + ".png");
					imageResource.setCacheTime(0);
					Embedded image = new Embedded(name, imageResource);
					image.requestRepaint();

					String filename = name;
					imageResource.setFilename(filename);
					component = image;
				}
				return component;
			}
		});

		articles.setContainerDataSource(bicycles);
		articles.setWidth("42%");
		articles.setVisibleColumns("articleID", "articleName", "articleType", "image");
		articles.setPageLength(10);

		articles.setColumnExpandRatio("image", 1);

		articles.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				articleForm.edit(bicycles.getItem(event.getItemId()), event.getItemId());

			}
		});
	}
	
	public JPAContainer<Bicycle> getBicycles(){
		return this.bicycles; 
		
	}
	
	private void filterContacts(String filter){
		
		if (bicycles.getFilters() != null) {
			this.bicycles.removeAllContainerFilters();
		}
		this.bicycles.addContainerFilter(new Or(new Like("articleID", filter + "%"),
				new Like("articleType", filter + "%"), new Like("articleName", filter + "%")));
	}
	
	

}