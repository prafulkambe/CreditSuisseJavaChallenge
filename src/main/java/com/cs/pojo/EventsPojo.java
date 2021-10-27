package com.cs.pojo;


public class EventsPojo {
	private String id;
	private long duration;
	private boolean alert;
	private String type;
	private String host;

	private EventsPojo(EventsPojoBuilder builder){
		this.id = builder.eventid;
		this.duration = builder.eventduration;
		this.alert = builder.alert;
		this.type = builder.type;
		this.host = builder.host;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public long getDuration() {
		return duration;
	}

	public boolean getAlert() {
		return alert;
	}

	public String getType() {
		return type;
	}
	
	public String getHost() {
		return host;
	}


	public static class EventsPojoBuilder {
		private String eventid;
		private long eventduration;
		private boolean alert;
		private String type;
		private String host;

		// id, state and timestamp are available for each msg so passing in constructor
		public EventsPojoBuilder(String eventid, long eventduration, boolean alert) {
			this.eventid = eventid;
			this.eventduration = eventduration;
			this.alert = alert;
		}

		// Type and Host are not available for all log messages

		public EventsPojoBuilder setType(String type) {
			this.type = type;
			return this;
		}

		public EventsPojoBuilder setHost(String host) {
			this.host = host;
			return this;
		}

		public EventsPojo build() {

			return new EventsPojo(this);
		}
	}


}
