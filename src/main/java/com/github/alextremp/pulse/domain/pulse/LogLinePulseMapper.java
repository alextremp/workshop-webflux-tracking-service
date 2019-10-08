package com.github.alextremp.pulse.domain.pulse;

import reactor.core.publisher.Mono;

/**
 * This is intentionally placed to an incorrect place :)
 */
public class LogLinePulseMapper {

  private static final String PULSE = "PULSE";
  private static final String SEP = " | ";

  public Mono<String> pulse2logLine(Pulse pulse) {
    return Mono.fromCallable(() -> new StringBuilder())
        .map(sb -> sb.append(PULSE)
            .append(SEP).append("pulse_id: ").append(pulse.getId())
            .append(SEP).append("pulse_clientId: ").append(pulse.getClientId())
            .append(SEP).append("pulse_name: ").append(pulse.getName()))
        .map(sb -> {
          pulse.getPayload().forEach((k, v) -> sb.append(SEP).append(k).append(": ").append(v));
          return sb.toString();
        });
  }
}
