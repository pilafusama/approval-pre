package com.tenpay.wxwork.approval.presvr.common.biz.model;

/**
 * ����
 * @author chancehe
 *
 */
public abstract class AbstractConstant {
	
	public final static String ENCODING = "GBK";
	public final static String DEFAULT_DATE_PATTERN = "yyyyMMdd";
	
	public final static String PLS_CFT_KEYID = "plsCftKeyId";

	public static abstract class AbstractHttpServerConfig{
		//����·��
		public static final String CONTEXT_PATH = "/";
		//���˷���servlet��·��
		public static final String STATEMENT_SERVICE_PATH = "/loan";
		
	}
	
	public static abstract class AbstractConfigFileName{
		public static final String COMMON_PROPERTIES = "common.properties";
	}
	
	public static abstract class AbstractCommonPropertiesKey{
		public static final String SERVICE_IP = "service.ip";
		public static final String SERVICE_PORT = "service.port";
		public static final String SERVICE_IPLIST = "service.ipList";
	}
	
	/**
	 * ��Ӧ״̬
	 * @author chancehe
	 *
	 */
	public enum ResponseState{
		SUCCESS,FAIL
	}
	
	/**
	 * ����״̬
	 * @author chancehe
	 *
	 */
	public enum StatementState{
		PROCESSING,SUCCESS,FAIL,DISABLED
	}
	
	/**
	 * ��ʽ��״̬
	 * @author chancehe
	 *
	 */
	public enum FormatState{
		SUCCESS,FAIL,EXISTS
	}
	
	/**
	 * ���˲���
	 * @author chancehe
	 *
	 */
	public enum StatementStep{
		//��ʼ
		INIT,
		//��ȡ���ж��˵�
		FETCH_BANK_FILE,
		//���ɱ�׼�ļ�
		CREATE_STANDARD_FILE
	}
	
	/**
	 * ���ɶ����ļ�����
	 * @author chancehe
	 *
	 */
	public static abstract class AbstractFormatConstant {
		// header��󳤶�
		public static final long HEADER_MAX_LEN = 1024 * 1024 * 1024;
		// bottom��󳤶�
		public static final long BOTTOM_MAX_LEN = 1024 * 1024 * 1024;
		//
		public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	}

	public enum ColumnParserState {
		// �����ɹ�
		SUCCESS,
		// �����쳣
		EXCEPTION,
		// ��������
		DISCARD,
	}

	public enum ThreadLocalKey {
		CONFIG, CUSTOM
	}

	public static abstract class AbstractLogAppender {
		public static final String DEFAULT = "default";

	}

	public static abstract class AbstractStatementService {
		public static final long TIME_DIFF = 1000 * 60 * 5;
	}
}
