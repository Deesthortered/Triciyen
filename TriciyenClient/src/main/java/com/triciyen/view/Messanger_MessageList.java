package com.triciyen.view;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import com.triciyen.Messanger_Connection;
import com.triciyen.model.Messanger_Content;

public class Messanger_MessageList {
	
	@FXML
	private VBox vbox_messages;
	@FXML
	private ListView<Messanger_Content> listviewMessages;
	
	private ObservableList<Messanger_Content> data;
	private List<Messanger_Content> list;
	
	public void initMessageList() throws ClassNotFoundException, SQLException{
		listviewMessages.setItems(handleMessageList());
		listviewMessages.setCellFactory(new Callback<ListView<Messanger_Content>, ListCell<Messanger_Content>>() {
			
			@Override
			public ListCell<Messanger_Content> call(ListView<Messanger_Content> param) {
				return new MessageListCell();
			}
		});
	}
	
	public void updateMessageList() throws ClassNotFoundException, SQLException{
		listviewMessages.getItems().clear();
		listviewMessages.getItems().addAll(handleMessageList());
	}
	
	static class MessageListCell extends ListCell<Messanger_Content> {
		@SuppressWarnings("static-access")
		@Override
        public void updateItem(Messanger_Content messangerContent, boolean empty) {
            super.updateItem(messangerContent, empty);
            if(empty){
            	setText(null);
            	setGraphic(null);
            } else {
            	setText(null);
                
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(4);
                grid.setPadding(new Insets(0, 10, 0, 10));
                
                messangerContent.getRoomName().setPrefWidth(100);
                messangerContent.getContent().setPrefWidth(100);
                
                grid.add(messangerContent.getProfileButton(), 0, 0, 1, 2);
                grid.add(messangerContent.getRoomName(), 1, 0);
                grid.add(messangerContent.getContent(), 1, 1);
                
                grid.setHgrow(messangerContent.getContentDateLabel(), Priority.ALWAYS);
                grid.setHalignment(messangerContent.getContentDateLabel(), HPos.RIGHT);
                grid.add(messangerContent.getContentDateLabel(), 2, 0, 1, 2);
                setGraphic(grid);
                
            } 
        }
    }

    public ObservableList<Messanger_Content> handleMessageList() throws SQLException, ClassNotFoundException {
    	Messanger_Connection conn = new Messanger_Connection();
        PreparedStatement ps = null;
        ResultSet rs = null;
    	list = new ArrayList<Messanger_Content>();
		
		String sql = "select content_date, content, content_room  "
				+ "from content  "
				+ "where content_room like ? "
				+ "order by content_date desc ";
		ps = conn.getConnection().prepareStatement(sql);
		ps.setString(1, "%"+Messanger_FriendsList.messangerMyProfile.getPersonNumber()+"%");
		rs = ps.executeQuery();
		
		while(rs.next()){
			Messanger_Content vo = new Messanger_Content();
			vo.getContentDateLabel().setText(rs.getString(1));
			vo.getContent().setText(rs.getString(2));
			vo.getRoom().setText(rs.getString(3));
			vo.getProfileButton().setText("hi");
			list.add(vo);
		}
		
		conn.getConnection().close();
		
		data = FXCollections.observableArrayList(subString(list));
		
		return data;
    }

    public List<Messanger_Content> subString(List<Messanger_Content> list) throws ClassNotFoundException, SQLException{
    	List<Messanger_Content> roomList;
    	List<String> subList = new ArrayList<>();
    	
    	roomList = list;
    	
    	for(int i = 0; i < roomList.size(); i++){
    		if(roomList.get(i).getRoom().getText().startsWith(Messanger_FriendsList.messangerMyProfile.getPersonNumber())){
    			subList.add(roomList.get(i).getRoom().getText().replace(Messanger_FriendsList.messangerMyProfile.getPersonNumber()+",", ""));
    			roomList.get(i).getRoom().setText(subList.get(i));
    		} else{
    			subList.add(roomList.get(i).getRoom().getText().replaceFirst(","+Messanger_FriendsList.messangerMyProfile.getPersonNumber(), ""));
    			roomList.get(i).getRoom().setText(subList.get(i));
    		}
    	}
    	
    	for(int j = 0; j < Messanger_FriendsList.list.size(); j++){
    		for(int i = 0; i < roomList.size(); i++){
    			if(Messanger_FriendsList.list.get(j).getPersonNumber().equals(roomList.get(i).getRoom().getText())){
    				roomList.get(i).getRoomName().setText(Messanger_FriendsList.list.get(j).getPersonName().getText());
    			} 
    		}
		}
    	
    	return roomList;
    }
    
    public static int selector;

    double isX = Messanger_Panel.messangerList.getPrimaryStage().getX();
    double isY = Messanger_Panel.messangerList.getPrimaryStage().getY();
    
    protected void handleList(){
    	listviewMessages.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2){
					selector = listviewMessages.getSelectionModel().getSelectedIndex();
					
					for(int i = 0; i < Messanger_FriendsList.list.size(); i++){
						if(Messanger_FriendsList.list.get(i).getPersonNumber().equals(listviewMessages.getItems().get(selector).getRoom().getText())){
							if(Messanger_FriendsList.messageDialogFlag.get(i) == false){
								Messanger_FriendsList.messageDialogFlag.set(i, true);
								
								if(Messanger_Panel.messangerList.getPrimaryStage().getX() == isX){
									Messanger_Panel.messangerList.getTestStage().get(i).setX(Messanger_Panel.messangerList.getPrimaryStage().getX()+500+Messanger_FriendsList.x);
									Messanger_Panel.messangerList.getTestStage().get(i).setY(Messanger_Panel.messangerList.getPrimaryStage().getY()+Messanger_FriendsList.y);
									Messanger_FriendsList.x += 20;
									Messanger_FriendsList.y += 20;
									try {
										Messanger_FriendsList.messageDialog.messageDialog(Messanger_FriendsList.list.get(i), i);
										break;
									} catch (ClassNotFoundException
											| IOException | SQLException
											| InterruptedException e) {
										e.printStackTrace();
									}
								} else{
									isX = Messanger_Panel.messangerList.getPrimaryStage().getX();
								    isY = Messanger_Panel.messangerList.getPrimaryStage().getY();
								    Messanger_FriendsList.x = 20.0;
								    Messanger_FriendsList.y = 20.0; 
									Messanger_Panel.messangerList.getTestStage().get(i).setX(isX+500);
									Messanger_Panel.messangerList.getTestStage().get(i).setY(isY);
									try {
										Messanger_FriendsList.messageDialog.messageDialog(Messanger_FriendsList.list.get(i), i);
										break;
									} catch (ClassNotFoundException
											| IOException | SQLException
											| InterruptedException e) {
										e.printStackTrace();
									}
								}
							} else{
								if(Messanger_Panel.messangerList.getTestStage().get(i).isShowing() == false){
									Messanger_Panel.messangerList.getTestStage().get(i).show();
									Messanger_LoginController.pool.startHandle(i);
									break;
								} else{
									Messanger_Panel.messangerList.getTestStage().get(i).toFront();
									break;
								}
							}
						}
					}
				}
			}
		});
    }
    
	public Messanger_MessageList() throws ClassNotFoundException, SQLException{
		listviewMessages = new ListView<Messanger_Content>();
	}
}
