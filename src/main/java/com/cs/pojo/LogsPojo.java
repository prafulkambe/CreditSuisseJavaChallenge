package com.cs.pojo;

public class LogsPojo {

	private String id;
	private String state;
	private String type;
	private String host;
	private long timestamp;

	private LogsPojo(LogsPojoBuilder builder){
		this.id = builder.id;
		this.state = builder.state;
		this.type = builder.type;
		this.host = builder.host;
		this.timestamp = builder.timestamp;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public String getType() {
		return type;
	}

	public String getHost() {
		return host;
	}

	public long getTimestamp() {
		return timestamp;
	}
	

	public static class LogsPojoBuilder {
		private String id;
		private String state;
		private String type;
		private String host;
		private long timestamp;	

		// id, state and timestamp are available for each msg so passing in constructor
		public LogsPojoBuilder(String id, String state, long timestamp) {
			this.id = id;
			this.state = state;
			this.timestamp = timestamp;
		}

		// Type and Host are not available for all log messages

		public LogsPojoBuilder setType(String type) {
			this.type = type;
			return this;
		}

		public LogsPojoBuilder setHost(String host) {
			this.host = host;
			return this;
		}

		public LogsPojo build() {

			return new LogsPojo(this);
		}
	}

}
