import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String chatHistory = "";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "This is a chat server. The messages written in the past are displayed here: " + chatHistory;
        } 
        
        else if (url.getPath().contains("/add-message")) {
            String[] parameters = url.getQuery().split("&");
            String[] separatedParamsString = parameters[0].split("=");
            String[] separatedParamsUser = parameters[1].split("=");

            if (separatedParamsString[0].equals("s")) {
                if (separatedParamsUser[0].equals("user")) {
                    chatHistory += String.format("%s: %s", separatedParamsUser[1], separatedParamsString[1]) + "\n";
                    return chatHistory;
                }
            }
        }

        return "404 Not Found!";
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
