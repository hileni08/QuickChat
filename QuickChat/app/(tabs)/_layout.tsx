import { Tabs } from "expo-router";
import { View, StyleSheet, Platform } from "react-native";

export default function TabsLayout() {
  return (
    <Tabs
      screenOptions={{
        headerShown: false,
        tabBarStyle: styles.tabBar,
        tabBarLabelStyle: styles.tabLabel,
        tabBarActiveTintColor: "#4B7BEC",
        tabBarInactiveTintColor: "#8e8e93",
      }}
    >
      <Tabs.Screen
        name="index"
        options={{ title: "Chat" }}
      />
      <Tabs.Screen
        name="history"
        options={{ title: "History" }}
      />
      <Tabs.Screen
        name="explore"
        options={{ title: "Explore" }}
      />
    </Tabs>
  );
}

const styles = StyleSheet.create({
  tabBar: {
    height: Platform.OS === "web" ? 60 : 70,
    paddingBottom: Platform.OS === "web" ? 10 : 15,
    paddingTop: 10,
    backgroundColor: "#fff",
    borderTopWidth: 0.5,
    borderTopColor: "#ccc",
    shadowColor: "#000",
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 10,
  },
  tabLabel: {
    fontSize: 14,
    fontWeight: "600",
  },
});
