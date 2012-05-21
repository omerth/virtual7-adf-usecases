package com.virtual7.textMessenger.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class DemoModel {
    private static final String CONNECTION_URL = "jdbc:oracle:thin:hr/hr@//localhost:1521/XE";

    private DemoModel() {
        super();
    }

    private static DemoModel model = new DemoModel();

    public static DemoModel getModel() {
        return model;
    }

    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<Message>();


        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Get a connection to the database
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Get a statement from the connection
            stmt = conn.prepareStatement("SELECT * FROM MESSAGES");

            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Message msg = new Message();
                msg.setId(res.getInt(1));
                msg.setContent(res.getString(2));
                messages.add(msg);
            }


        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
        return messages;
    }

    public void saveMessage(String messageContent) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Get a connection to the database
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Get a statement from the connection
            stmt = conn.prepareStatement("INSERT INTO MESSAGES (ID,CONTENT) VALUES(MESSAGES_SEQ.NEXTVAL,?)");
            stmt.setString(1, messageContent);
            stmt.execute();

            conn.commit();

        } catch (SQLException exc) {
            exc.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }
    
    public void deleteMessage(String messageID){
      Connection conn = null;
      PreparedStatement stmt = null;
      try {
          // Get a connection to the database
          conn = DriverManager.getConnection(CONNECTION_URL);

          // Get a statement from the connection
          stmt = conn.prepareStatement("DELETE FROM MESSAGES WHERE ID = ?");
          stmt.setString(1, messageID);
          stmt.execute();

          conn.commit();

      } catch (SQLException exc) {
          exc.printStackTrace();
          if (conn != null) {
              try {
                  conn.rollback();
              } catch (SQLException se) {
                  se.printStackTrace();
              }
          }
      } finally {
          if (stmt != null) {
              try {
                  stmt.close();
              } catch (SQLException se) {
                  se.printStackTrace();
              }
          }
          if (conn != null) {
              try {
                  conn.close();
              } catch (SQLException se) {
                  se.printStackTrace();
              }
          }
      }
    }
    
}
