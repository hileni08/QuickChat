import { Redirect } from "expo-router";

export default function Index() {
  // Always redirect to login first
  return <Redirect href="/(auth)/login" />;
}
