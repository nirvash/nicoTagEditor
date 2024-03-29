package com.github.nirvash.nicoTagEditor;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.github.nirvash.nicoTagEditor.ItemSelectionListener.viewUpdateListener;


public class mainPanel extends JFrame {

	public class ViewUpdateListener implements viewUpdateListener {
		@Override
		public void updateView(ListItem item) {
			isUpdatingView = true;
			updateTagItems(item);
			isUpdatingView = false;
			updateHtmlView(item, true, false);
		}

		@Override
		public void commit(ListItem item) {
			mainPanel.this.commit(item);
		}

	}

	private boolean isUpdatingView = false;
	private static final long serialVersionUID = 1L;
	private JPanel jPanelTop;
	private JButton jButtonSave;
	private JButton jButtonSetTag;
	
	private JSplitPane jSplitPane0;
	private JPanel jPanelList;
	private JButton jButtonClear;
	private JPanel jListButtonPanel;
	private JList jFileList;
	private MusicListModel musicModel;
	private JScrollPane jScrollPaneFileList;	
	private ItemSelectionListener itemSelectionListener;

	private JPanel jPanelTagEdit;
	
	private JLabel jLabelAlbum;
	private JLabel jLabelTitle;
	private JLabel jLabelArtist;
	private JTextField jTextFieldAlbum;
	private JTextField jTextFieldTitle;
	private JTextField jTextFieldArtist;
	private JButton jButtonGetHtml;
	private JButton jButtonSwapAlbumArtist;
	
	private JScrollPane jScrollPaneHtml;
	private JEditorPane jEditorPane;

	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	static {
	}
	
	public mainPanel() {
		initComponents();
	}

	private void initComponents() {
		add(getJPanelTop(), BorderLayout.NORTH);
		add(getJSplitPane0(), BorderLayout.CENTER);
		setSize(750, 672);
	}
	
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
			jEditorPane.setEditable(false);
			jEditorPane.addHyperlinkListener(new HyperlinkListener() {
				@Override
				public void hyperlinkUpdate(HyperlinkEvent e) {
					if (e.getEventType() == EventType.ACTIVATED) {	// If clicked
						URL url = e.getURL();
						// Launch default browser
						Desktop dp = Desktop.getDesktop();
						try {
							dp.browse(url.toURI());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			});
		}
		return jEditorPane;
	}
	
	private JScrollPane getJScrollPaneHtml() {
		if (jScrollPaneHtml == null) {
			jScrollPaneHtml = new JScrollPane(getJEditorPane());
			jScrollPaneHtml.setAutoscrolls(true);
			
			jScrollPaneHtml.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                	jEditorPane.setSize(new Dimension(
                			jScrollPaneHtml.getWidth()-20, 
                			jScrollPaneHtml.getHeight()-20));
                }
			});	

		}
		return jScrollPaneHtml;
	}
	
	private JTextField getJTextFieldAlbum() {
		if (jTextFieldAlbum == null) {
			jTextFieldAlbum = new JTextField();
			jTextFieldAlbum.setText("");
			jTextFieldAlbum.setAutoscrolls(true);
			javax.swing.text.Document doc = jTextFieldAlbum.getDocument();
			doc.addDocumentListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					update();
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					update();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					update();
				}
				
				private void update() {
					if (isUpdatingView) return;
					ListItem listItem = (ListItem)jFileList.getSelectedValue();
					listItem.setAlbum(jTextFieldAlbum.getText());
					int index = jFileList.getSelectedIndex();
					musicModel.updateItem(index, index);
				}
				
			});
		}
		return jTextFieldAlbum;
	}

	private JTextField getJTextFieldTitle() {
		if (jTextFieldTitle == null) {
			jTextFieldTitle = new JTextField();
			jTextFieldTitle.setText("");
			jTextFieldTitle.setAutoscrolls(true);
			javax.swing.text.Document doc = jTextFieldTitle.getDocument();
			doc.addDocumentListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					update();
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					update();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					update();
				}
				
				private void update() {
					if (isUpdatingView) return;
					ListItem listItem = (ListItem)jFileList.getSelectedValue();
					listItem.setTitle(jTextFieldTitle.getText());
					int index = jFileList.getSelectedIndex();
					musicModel.updateItem(index, index);
				}
				
			});
		}
		return jTextFieldTitle;
	}

	private JTextField getJTextFieldArtist() {
		if (jTextFieldArtist == null) {
			jTextFieldArtist = new JTextField();
			jTextFieldArtist.setText("");
			jTextFieldArtist.setAutoscrolls(true);
			javax.swing.text.Document doc = jTextFieldArtist.getDocument();
			doc.addDocumentListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					update();
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					update();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					update();
				}
				
				private void update() {
					if (isUpdatingView) return;
					ListItem listItem = (ListItem)jFileList.getSelectedValue();
					listItem.setArtist(jTextFieldArtist.getText());
					int index = jFileList.getSelectedIndex();
					musicModel.updateItem(index, index);
				}
				
			});
		}
		return jTextFieldArtist;
	}


	private JLabel getJLabelAlbum() {
		if (jLabelAlbum == null) {
			jLabelAlbum = new JLabel();
			jLabelAlbum.setText("Album");
		}
		return jLabelAlbum;
	}

	private JLabel getJLabelTitle() {
		if (jLabelTitle == null) {
			jLabelTitle = new JLabel();
			jLabelTitle.setText("Title");
		}
		return jLabelTitle;
	}
	
	private JLabel getJLabelArtist() {
		if (jLabelArtist == null) {
			jLabelArtist = new JLabel();
			jLabelArtist.setText("Artist");
		}
		return jLabelArtist;
	}
	
	private JButton getJButtonGetHtml() {
		if (jButtonGetHtml == null) {
			jButtonGetHtml = new JButton();
			jButtonGetHtml.setText("Get Description");
			jButtonGetHtml.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ListItem listItem = (ListItem)jFileList.getSelectedValue();
					updateHtmlView(listItem, true, true);
				}
			});
		}
		return jButtonGetHtml;
	}
	
	private JButton getJButtonSwapAlbumArtist() {
		if (jButtonSwapAlbumArtist == null) {
			jButtonSwapAlbumArtist = new JButton();
			jButtonSwapAlbumArtist.setText("Swap album and artist");
			jButtonSwapAlbumArtist.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ListItem listItem = (ListItem)jFileList.getSelectedValue();
					String artist = listItem.getAlbum();
					String album = listItem.getArtist();
					listItem.setArtist(artist);
					listItem.setAlbum(album);
					updateTagItems(listItem);
				}
			});
		}
		return jButtonSwapAlbumArtist;
	}

	private JPanel getJPanelTagEdit() {
		if (jPanelTagEdit == null) {
			jPanelTagEdit = new JPanel();
			GroupLayout layout = new GroupLayout(jPanelTagEdit);
			jPanelTagEdit.setLayout(layout);
			
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			
			layout.setHorizontalGroup(layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(getJLabelAlbum())
									.addComponent(getJLabelTitle())
									.addComponent(getJLabelArtist())
									)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(getJTextFieldAlbum())
									.addComponent(getJTextFieldTitle())
									.addComponent(getJTextFieldArtist())
									)
							)
					.addGroup(layout.createSequentialGroup()
							.addComponent(getJButtonGetHtml())
							.addComponent(getJButtonSwapAlbumArtist())
							)
					.addComponent(getJScrollPaneHtml())
				);
			
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(getJLabelAlbum())
							.addComponent(getJTextFieldAlbum()))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(getJLabelTitle())
							.addComponent(getJTextFieldTitle()))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(getJLabelArtist())
							.addComponent(getJTextFieldArtist()))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(getJButtonGetHtml())
							.addComponent(getJButtonSwapAlbumArtist()))
					.addComponent(getJScrollPaneHtml())
				);
		}
		return jPanelTagEdit;
	}

	private JScrollPane getJScrollPaneFileList() {
		if (jScrollPaneFileList == null) {
			jScrollPaneFileList = new JScrollPane();
			jScrollPaneFileList.setViewportView(getJFileList());
		}
		return jScrollPaneFileList;
	}

	private JList getJFileList() {
		if (jFileList == null) {
			jFileList = new JList();
			musicModel = new MusicListModel();
			jFileList = new JList(musicModel);
		    jFileList.setTransferHandler(new FileDropHandler(musicModel));
		    jFileList.setCellRenderer(new FileRenderer());
		    jFileList.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		    jFileList.setDropMode(DropMode.INSERT);
		    jFileList.setDragEnabled(true);
		    jFileList.setBackground(Color.WHITE);
		    jFileList.setSelectionBackground(new Color(200, 200, 255));
		    jFileList.addListSelectionListener(getItemSelectionListener(musicModel));
			jFileList.setModel(musicModel);
		}
		return jFileList;
	}

	private ItemSelectionListener getItemSelectionListener(MusicListModel fileListModel) {
		if (itemSelectionListener == null) {
			itemSelectionListener = new ItemSelectionListener(new ViewUpdateListener(), fileListModel);
		}
		return itemSelectionListener;
	}

	private JPanel getJListButtonPanel() {
		if (jListButtonPanel == null) {
			jListButtonPanel = new JPanel();
			jListButtonPanel.setPreferredSize(new Dimension(100, 30));
			jListButtonPanel.setLayout(new BoxLayout(jListButtonPanel, BoxLayout.X_AXIS));
			jListButtonPanel.add(getJButtonClear());
		}
		return jListButtonPanel;
	}

	private JButton getJButtonClear() {
		if (jButtonClear == null) {
			jButtonClear = new JButton();
			jButtonClear.setText("Clear");
			jButtonClear.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DefaultListModel model = (DefaultListModel)jFileList.getModel();
					model.clear();
				}
			});
		}
		return jButtonClear;
	}

	private JPanel getJPanelList() {
		if (jPanelList == null) {
			jPanelList = new JPanel();
			jPanelList.setLayout(new BorderLayout());
			jPanelList.add(getJListButtonPanel(), BorderLayout.SOUTH);
			jPanelList.add(getJScrollPaneFileList(), BorderLayout.CENTER);
		}
		return jPanelList;
	}

	private JSplitPane getJSplitPane0() {
		if (jSplitPane0 == null) {
			jSplitPane0 = new JSplitPane();
			jSplitPane0.setDividerLocation(300);
			jSplitPane0.setDividerSize(5);
			jSplitPane0.setLeftComponent(getJPanelList());
			jSplitPane0.setRightComponent(getJPanelTagEdit());
		}
		return jSplitPane0;
	}

	private JPanel getJPanelTop() {
		if (jPanelTop == null) {
			jPanelTop = new JPanel();
			jPanelTop.setPreferredSize(new Dimension(100, 30));
			jPanelTop.setLayout(new BoxLayout(jPanelTop, BoxLayout.X_AXIS));
			jPanelTop.add(getJButtonSave());
			jPanelTop.add(getJButtonSetTag());
		}
		return jPanelTop;
	}
	
	private JButton getJButtonSave() {
		if (jButtonSave == null) {
			jButtonSave = new JButton();
			jButtonSave.setText("Save");
			jButtonSave.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ListItem item = (ListItem)jFileList.getSelectedValue();
					commit(item);
					save();
				}
			});
		}
		return jButtonSave;
	}
	
	private JButton getJButtonSetTag() {
		if (jButtonSetTag == null) {
			jButtonSetTag = new JButton();
			jButtonSetTag.setText("Set Tag");
			jButtonSetTag.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Object[] items = jFileList.getSelectedValues();
					for (Object item : items) {
						ListItem listItem = (ListItem)item;
						listItem.analyzeTag();
					}
					ListItem current = (ListItem)jFileList.getSelectedValue();
					if (current != null) {
						updateTagItems(current);
					}
					int[] indicies = jFileList.getSelectedIndices();
					int begin = indicies[0];
					int end = indicies[indicies.length-1];
					musicModel.updateItem(begin, end);
				}
			});
		}
		return jButtonSetTag;
	}


	protected void save() {
		Object[] items = jFileList.getSelectedValues();
		for (Object obj : items) {
			ListItem item = (ListItem)obj;
			item.save();
		}
		
		int[] indicies = jFileList.getSelectedIndices();
		int begin = indicies[0];
		int end = indicies[indicies.length-1];
		musicModel.updateItem(begin, end);

	}

	private void commit(ListItem item) {
		if (item != null) {
			item.setAlbum(getJTextFieldAlbum().getText());
			item.setTitle(getJTextFieldTitle().getText());
			item.setArtist(getJTextFieldArtist().getText());
			jFileList.invalidate();
		}
	}

	private void updateTagItems(ListItem item) {
		getJTextFieldAlbum().setText(item.getAlbum());
		getJTextFieldTitle().setText(item.getTitle());
		getJTextFieldArtist().setText(item.getArtist());
	}

	private void updateHtmlView(ListItem item, boolean isConnect, boolean forceUpdate) {
		String description = "";
		if (!forceUpdate && item.hasDescritpion()) {
			description = item.getDescription();
		} else {
			String id = item.getId();
			String urlHtml = "http://www.nicovideo.jp/watch/" +id;
			if (id != null) {
				try {
					String filename = item.getFile().getName();
//					filename = filename.replaceAll("(sm|nm)\\d*_", "");
					description = String.format("%s<br><a href=\"%s\">%s</a><br>", filename, urlHtml, urlHtml);
					if (isConnect) {
						String urlXml = "http://ext.nicovideo.jp/api/getthumbinfo/" + id;
						String urlThumb = "http://ext.nicovideo.jp/thumb/" + id;
						String thumb = "";
						
						Document document = Jsoup.connect(urlXml).get();
						Elements elems = document.select("thumbnail_url");
						if (!elems.isEmpty()) {
							String image_url = elems.first().text();
							thumb = String.format("<img src=\"%s\">", elems.first().text());
							item.setArtwork(image_url);
						}
						document = Jsoup.connect(urlHtml).get();
	/*					
						File f = new File("tmp.txt");
						FileWriter w = new FileWriter(f);
						w.write(document.outerHtml());
						w.flush(); w.close();
	*/
						String userName = getUserNmaeFromHtml(document);
						if (userName != null && userName.length()>0) {
							userName = userName.replaceAll("<.*?>", "");
							description += userName + "<br>";
							if (item.getAlbum().length()==0) {
								item.setAlbum(userName);
								jTextFieldAlbum.setText(userName);
							}
						}
						
						description += thumb + "<br>";
						
						String html = getDescriptionFromHtml(document);
	//					String html = getDescriptionFromXML(document);
						if (html != null) {
							description += html;
						} else {
							html = document.body().html();
							description += html;
						}
					}
					item.setDescription(description);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		getJEditorPane().setContentType("text/html");
		getJEditorPane().setText(description);

	}

	private String getDescriptionFromXML(Document document) {
		Elements descs = document.select("description");
		Element desc = descs.first();
		if (desc != null) {
			return desc.html();
		}
		return null;
	}

	private String getUserNmaeFromHtml(Document document) {
		Elements users = document.select("strong[itemprop*=name]");
		Element user = users.first();
		if (user != null) {
			return user.html();
		}
		return null;
	}

	private String getDescriptionFromHtml(Document document) {
		Elements descs = document.select("p[itemprop*=description]");
		Element desc = descs.first();	
		if (desc == null) return null;
		return desc.html();
	}

	@SuppressWarnings("unused")
	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	/**
	 * Main entry of the class.
	 * Note: This class is only created so that you can easily preview the result at runtime.
	 * It is not expected to be managed by the designer.
	 * You can modify it as you like.
	 */
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainPanel frame = new mainPanel();
				frame.setDefaultCloseOperation(mainPanel.EXIT_ON_CLOSE);
				frame.setTitle("nicoTagEditor");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

}
