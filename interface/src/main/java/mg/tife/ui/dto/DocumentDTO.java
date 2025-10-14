package mg.tife.ui.dto;

import java.util.Map;

public record DocumentDTO(String id, String content, Map<String, Object> metadata) {}