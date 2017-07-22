package net.lipecki.sqcompanion.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerAuthentication {

	private String type;
	private String value;

}
