import { View, Text, TextInput, Button, FlatList, Platform } from "react-native";
import { useState } from "react";

export default function ChatScreen() {
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState<string[]>([]);

  const sendMessage = () => {
    if (message.trim() !== "") {
      setMessages([...messages, message]);
      setMessage("");
    }
  };

  return (
    <View
      style={{
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f5f5f5",
        padding: 20,
      }}
    >
      <View
        style={{
          width: Platform.OS === "web" ? 400 : "100%",
          flex: 1,
          maxHeight: 600,
          backgroundColor: "white",
          borderRadius: 12,
          padding: 20,
          shadowColor: "#000",
          shadowOffset: { width: 0, height: 2 },
          shadowOpacity: 0.25,
          shadowRadius: 3.84,
          elevation: 5,
        }}
      >
        <Text style={{ fontSize: 20, marginBottom: 10 }}>QuickChat 💬</Text>

        <FlatList
          data={messages}
          renderItem={({ item }) => (
            <Text style={{ padding: 5 }}>{item}</Text>
          )}
          keyExtractor={(item, index) => index.toString()}
          style={{ flex: 1, marginBottom: 10 }}
        />

        {/* TextInput + Button on the same line */}
        <View style={{ flexDirection: "row", alignItems: "center" }}>
          <TextInput
            placeholder="Type a message..."
            value={message}
            onChangeText={setMessage}
            style={{
              flex: 1,
              borderWidth: 1,
              padding: 10,
              borderRadius: 6,
              marginRight: 8,
            }}
          />
          <Button title="Send" onPress={sendMessage} />
        </View>
      </View>
    </View>
  );
}
