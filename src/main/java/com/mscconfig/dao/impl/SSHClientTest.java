package com.mscconfig.dao.impl;

import java.util.HashMap;

/**
 * User: Vladimir
 * Date: 24.05.13
 * Time: 10:59
 * Тестовые выводы комманд
 */
public class SSHClientTest {
	public static HashMap<String,String> cmdMap = new HashMap<String,String>();
	static { cmdMap.put("exemmlmx -c \"ZRQI:ROU:NAME=AIMSI;\" -n \"MSS-239663\"",
			"ZZRQI:ROU:NAME=AIMSI;\n" +
					"\n" +
					"\n" +
					"LOADING PROGRAM VERSION 17.31-0\n" +
					"\n" +
					"COMMAND EXECUTION STARTED -- PLEASE WAIT\n" +
					"\n" +
					"\n" +
					"MSCi      MSS_DONETSK               2013-05-24  11:18:11\n" +
					"\n" +
					"ROUTING ATTRIBUTE ANALYSIS SUBANALYSIS INTERROGATION\n" +
					"\n" +
					"SUBANALYSIS NAME:            AIMSI\n" +
					"STATE OF SUBANALYSIS:        NORMAL SIDE\n" +
					"ANALYSED ATTRIBUTE:          AIMSI\n" +
					"\n" +
					"RESULTS ON NORMAL SIDE:\n" +
					"-----------------------\n" +
					"DEFAULT RESULT:                               CDNBR    : SUBANALYSIS\n" +
					"RESULT OF UNKNOWN ATTRIBUTE:                  RIMSI    : SUBANALYSIS\n" +
					"\n" +
					"VALUE OF ATTRIBUTE:                           RESULT OF SUBANALYSIS:\n" +
					"----------------------------------------------------------------------- \n" +
					"0                                             LBCUID   : SUBANALYSIS\n" +
					"\n" +
					"COMMAND EXECUTED\n" +
					"\n" +
					"\n" +
					"ATTRIBUTE ANALYSIS HANDLING COMMAND <RQ_>\n" +
					"<\n" +
					"[pronkin@cs1 ~]$\n");
		cmdMap.put("exemmlmx -c \"ZRQI:ROU:NAME=LBCUID;\" -n \"MSS-239663\"",
				"ZZRQI:ROU:NAME=LBCUID;\n" +
						"\n" +
						"\n" +
						"LOADING PROGRAM VERSION 17.31-0\n" +
						"\n" +
						"COMMAND EXECUTION STARTED -- PLEASE WAIT\n" +
						"\n" +
						"\n" +
						"MSCi      MSS_DONETSK               2013-05-24  16:57:41\n" +
						"\n" +
						"ROUTING ATTRIBUTE ANALYSIS SUBANALYSIS INTERROGATION\n" +
						"\n" +
						"SUBANALYSIS NAME:            LBCUID\n" +
						"STATE OF SUBANALYSIS:        NORMAL SIDE\n" +
						"ANALYSED ATTRIBUTE:          LBCUID\n" +
						"\n" +
						"RESULTS ON NORMAL SIDE:\n" +
						"-----------------------\n" +
						"DEFAULT RESULT:                               CONTINUE : FINAL RESULT\n" +
						"RESULT OF UNKNOWN ATTRIBUTE:                  CONTINUE : FINAL RESULT\n" +
						"\n" +
						"VALUE OF ATTRIBUTE:                           RESULT OF SUBANALYSIS:\n" +
						"-----------------------------------------------------------------------\n" +
						"200                                           MGW07    : FINAL RESULT\n" +
						"\n" +
						"COMMAND EXECUTED\n" +
						"\n" +
						"\n" +
						"ATTRIBUTE ANALYSIS HANDLING COMMAND <RQ_>\n" +
						"<\n") ;
		cmdMap.put("exemmlmx -c \"ZJGI:MODE=1:MGWID=10:;\" -n \"MSS-239663\"","ZZJGI:MODE=1:MGWID=10:;\n" +
				"\n" +
				"\n" +
				"LOADING PROGRAM VERSION 8.34-0\n" +
				"\n" +
				"MSCi      MSS_DONETSK               2013-05-27  15:15:14\n" +
				"\n" +
				"MGW DATA:\n" +
				"\n" +
				"\n" +
				"MGW ID........................10\n" +
				"MGW NAME......................MGW7V01\n" +
				"MGW ADDRESS...................172.25.50.42\n" +
				"PORT NUMBER...................8009\n" +
				"DOMAIN NAME...................\n" +
				"ROUTE.........................0\n" +
				"MGW TYPE......................GENERAL\n" +
				"UNIT TYPE.....................SIGU\n" +
				"UNIT INDEX....................2\n" +
				"CTRL ADDRESS..................172.25.52.130\n" +
				"E.164 AESA....................380501111112\n" +
				"LOCAL BCU-ID..................200\n" +
				"DEFAULT PARAMETER SET.........0\n" +
				"PARAMETER SET ATTACHMENT......USE DEFAULT\n" +
				"MGW PROFILE...................NOKIA MGW PROFILE VER 26\n" +
				"REGISTRATION ALLOWANCE........ALLOWED\n" +
				"REGISTRATION STATUS...........REGISTERED\n" +
				"\n" +
				"AUDIT STATUS..................ALLOWED\n" +
				"LOCAL PORT....................2945\n" +
				"TRANSPORT TYPE................SCTP\n" +
				"SCTP PARAMETER SET NUMBER.....2\n" +
				"SCTP MODE.....................H248SCTP\n" +
				"LOAD REDUCTION PERCENTAGE.....0%\n" +
				"NETWORK DELAY TIME............5 *10 MSEC\n" +
				"\n" +
				"\n" +
				"H.248 PROTOCOL RELATED DATA:\n" +
				"\n" +
				"H.248 VERSION.................2\n" +
				"H.248 CODING..................ASN\n" +
				"\n" +
				"USED PARAMETER SET............0\n" +
				"\n" +
				"TIMERS:\n" +
				"NORMAL MG EXECUTION TIME.............100 *10 MSEC\n" +
				"NORMAL MGC EXECUTION TIME............150 *10 MSEC\n" +
				"MG PROVISIONAL RESPONSE TIME.........90 *10 MSEC\n" +
				"MGC PROVISIONAL RESPONSE TIME........250 *10 MSEC\n" +
				"NE HEARTBEAT TIME....................200 *10 MSEC\n" +
				"CONTEXT AND TERM HEARTBEAT TIME......60000 *10 MSEC\n" +
				"TW TIMER.............................2000 *10 MSEC\n" +
				"\n" +
				"H248 AUDITABLE PARAMETERS:\n" +
				"\n" +
				"TRFO..........................NOT ALLOWED\n" +
				"TFO 2G........................ALLOWED\n" +
				"MG ORIGINATED PENDING LIMIT...6\n" +
				"MGC ORIGINATED PENDING LIMIT..6\n" +
				"PACKAGE(S)....................0x0000 VER 1 (nat)\n" +
				"                              0x0001 VER 1 (g)\n" +
				"                              0x0002 VER 1 (root)\n" +
				"                              0x0003 VER 1 (tonegen)\n" +
				"                              0x0004 VER 1 (tonedet)\n" +
				"                              0x0005 VER 1 (dg)\n" +
				"                              0x0006 VER 1 (dd)\n" +
				"                              0x0007 VER 1 (cg)\n" +
				"                              0x000a VER 1 (ct)\n" +
				"                              0x000b VER 1 (nt)\n" +
				"                              0x000d VER 1 (tdmc)\n" +
				"                              0x001d VER 1 (an)\n" +
				"                              0x001e VER 1 (bcp)\n" +
				"                              0x0021 VER 1 (gb)\n" +
				"                              0x0022 VER 1 (bt)\n" +
				"                              0x0023 VER 1 (bcg)\n" +
				"                              0x0024 VER 1 (xcg)\n" +
				"                              0x0025 VER 1 (srvtn)\n" +
				"                              0x0026 VER 1 (xsrvtn)\n" +
				"                              0x0027 VER 1 (int)\n" +
				"                              0x0028 VER 1 (biztn)\n" +
				"                              0x0029 VER 1 (chp)\n" +
				"                              0x002f VER 1 (threegup)\n" +
				"                              0x0030 VER 1 (threegcsd)\n" +
				"                              0x0032 VER 1 (threegxcg)\n" +
				"                              0x0033 VER 1 (aasb)\n" +
				"                              0x0046 VER 1 (threegmlc)\n" +
				"                              0x0047 VER 1 (bannsyx)\n" +
				"                              0x0048 VER 1 (vvsyx)\n" +
				"                              0x0049 VER 1 (setsyx)\n" +
				"                              0x0082 VER 1 (threegcsden)\n" +
				"                              0x0083 VER 1 (threegiptra)\n" +
				"                              0x0084 VER 1 (threegflex)\n" +
				"                              0x00e3 VER 1 (threegint)\n" +
				"                              0x8005 VER 1 (nokiaiwf)\n" +
				"                              0x8006 VER 1 (nokiarootc)\n" +
				"                              0x8007 VER 2 (threegtrace)\n" +
				"                              0x800a VER 1 (nokiabcp)\n" +
				"                              0x800b VER 1 (nokiatestcall)\n" +
				"                              0x800c VER 1 (nokiaect)\n" +
				"                              0x8010 VER 1 (sctpstream)\n" +
				"SUPPORTED CODECS..............G711 64 A LAW\n" +
				"                              G711 56 A LAW\n" +
				"                              AMR UMTS\n" +
				"                              AMR UMTS 2\n" +
				"                              AMR FR\n" +
				"                              AMR HR\n" +
				"                              GSM EFR\n" +
				"                              GSM FR\n" +
				"                              GSM HR\n" +
				"                              CLEARMODE\n" +
				"                              TEL_EVENT\n" +
				"\n" +
				"SUPPORTED PACKETIZATION PERIOD FOR G711/G729 CODECS...G711 20 MS\n" +
				"PROVISIONED MGW CAPABILITIES:\n" +
				"\n" +
				"NUMBER         NAME                    VALUE     VALUE NAME\n" +
				"\n" +
				"   0    DIRECT TONE CONN                 N           -\n" +
				"   50   CODEC MODIFICATION CAPAB         2       ATM AAL2\n" +
				"   51   THROUGH CONN CAPAB               0       TOPOLOGY\n" +
				"   52   THROUGH CONN CTRL                0       TOPOLOGY\n" +
				"   53   CTM CTRL                         2       MSS CONTROLLED\n" +
				"\n" +
				"PROVISIONED CODECS:\n" +
				"                    CLEARMODE\n" +
				"                    TELEPHONE EVENT\n" +
				"\n" +
				"MGW OPTIONAL FEATURES:\n" +
				"\n" +
				"RTCP BW CONTROL                                SUPPORTED\n" +
				"APPL LAYER REDUNDANCY                          SUPPORTED\n" +
				"\n" +
				"COMMAND EXECUTED\n" +
				"\n" +
				"\n" +
				"MEDIA GATEWAY DATA HANDLING COMMAND <JG_>\n" +
				"<\n");
}

}
