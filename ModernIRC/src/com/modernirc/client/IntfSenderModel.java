package com.modernirc.client;

import java.io.IOException;

/**
 * <p align = "center">
 * 	<b><u>Interface intfSenderModel</u></b>
 * </p>
  * <p align = "left">
 * 	<b><u>M�thode :</b></u><br />
 * 	- + void setMsgToSend()<br />
 *  - + void quitServer() throws IOException<br />
 * </p>
 * @author SPAL/DDBE
 *
 */
public interface IntfSenderModel {
	public abstract void setMsgToSend(String msgToSend);
	public abstract void quitServer() throws IOException;
}