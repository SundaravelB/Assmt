import ratpack.server.RatpackServer;

public class Main {
    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server
                .handlers(chain -> chain
                        .get(ctx -> ctx.render("Ratpack Application!"))
                        .get("item", ctx -> ctx.render("{\"item\":\"Pizza\"}"))
                        .prefix("item/:id", prefixChain -> prefixChain
                                .all(ctx -> ctx.byMethod(r -> r
                                        .post(req -> req.render("Post Request"))
                                        .put(req -> req.render("Put Request"))
                                        .get(req -> req.render("Item " + req.getAllPathTokens().get("id"))))
                                )
                        )
                )
        );
    }
}