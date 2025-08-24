import { View, Text, FlatList, StyleSheet, Button, Platform } from "react-native";
import { ThemedView } from "@/components/ThemedView";
import { useRouter } from "expo-router";

type Contact = {
  id: string;
  name: string;
  phone: string;
};

// Example contacts
const contacts: Contact[] = [
  { id: "1", name: "Alice Johnson", phone: "+264 81 123 4567" },
  { id: "2", name: "Bob Smith", phone: "+264 81 987 6543" },
  { id: "3", name: "Charlie Brown", phone: "+264 81 555 1212" },
  { id: "4", name: "Diana Prince", phone: "+264 81 444 3333" },
  { id: "5", name: "Ethan Hunt", phone: "+264 81 222 1111" },
];

export default function ContactsScreen() {
  const router = useRouter();

  const handleChat = (contact: Contact) => {
    // Navigate to Chat tab, optionally pass contact info
    router.push({
      pathname: "/(tabs)/index",
      params: { contactName: contact.name, contactPhone: contact.phone },
    });
  };

  return (
    <ThemedView style={styles.container}>
      <Text style={styles.title}>Contacts</Text>
      <FlatList
        data={contacts}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <View style={styles.contactItem}>
            <Text style={styles.contactName}>{item.name}</Text>
            <Text style={styles.contactPhone}>{item.phone}</Text>
            <View style={{ marginTop: 8, alignSelf: "flex-start" }}>
              <Button title="Chat" onPress={() => handleChat(item)} />
            </View>
          </View>
        )}
      />
    </ThemedView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5f5f5",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
  },
  contactItem: {
    backgroundColor: "white",
    padding: 15,
    marginBottom: 10,
    width: Platform.OS === "web" ? 400 : "100%",
    borderRadius: 8,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 5,
  },
  contactName: {
    fontSize: 16,
    fontWeight: "600",
  },
  contactPhone: {
    fontSize: 14,
    color: "gray",
    marginTop: 4,
  },
});
