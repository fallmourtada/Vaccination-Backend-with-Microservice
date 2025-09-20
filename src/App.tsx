import { ThemeProvider } from "@/components/shared/theme-provider";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PublicRouter from './pages/public/public-router';
import AdminRouter from "./pages/admin/admin-router";
import LandingPage from "./pages/landing-page";
import LoginPage from "./pages/login-page";
import { Toaster } from "@/components/ui/sonner";


function App() {

  return (
    // Le ThemeProvider gère le thème de l'application (clair/sombre)
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">

    <Router>
      <Routes>

          {/* Landing page */}
          <Route path="/" element={<LandingPage />} />

          {/* Page de connexion */}
          <Route path="/login" element={<LoginPage />} />

          {/* Routes publiques */}
          <Route path="/medecin/*" element={<PublicRouter />}/>

          {/* Routes admin */}
          <Route path="/admin/*" element={<AdminRouter />} />

      </Routes>

    </Router>

    {/* Toaster pour les notifications */}
    <Toaster 
      // position="top-right"
      richColors
      closeButton
    />

    </ThemeProvider>
  )
}

export default App
