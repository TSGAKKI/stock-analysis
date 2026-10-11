import "./globals.css";
export const metadata = { title: "Stock Facts", description: "Source-backed market facts" };
export default function Layout({ children }: Readonly<{ children: React.ReactNode }>) { return <html lang="en"><body>{children}</body></html>; }
