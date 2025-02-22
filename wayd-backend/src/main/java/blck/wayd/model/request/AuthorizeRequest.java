package blck.wayd.model.request;

/**
 * Authorize Request.
 */
public record AuthorizeRequest(String username, String rawPassword) {
}
