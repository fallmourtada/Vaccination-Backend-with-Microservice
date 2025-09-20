import { Route, Routes } from "react-router-dom";
import AdminDashboard from "./dashboard";
import AdminLayout from "./layout";
import UserPage from "./user";
import RolePermissionPage from "./role-permission";
import LocalitePage from "./localite";
import CentrePage from "./centre";
import Rapport from "../public/rapport";
import ConfigurationPage from "./configuration";
import MessagePage from "./message";


export default function AdminRouter() {
  return (
    <Routes>
      <Route element={<AdminLayout />}>
        <Route path="dashboard" element={<AdminDashboard />} />
        <Route path="users" element={<UserPage />} />
        <Route path="roles" element={<RolePermissionPage />} />
        <Route path="localites" element={<LocalitePage />} />
        <Route path="centres" element={<CentrePage />} />
        <Route path="rapports" element={<Rapport />} />
        <Route path="configuration" element={<ConfigurationPage />} />
        <Route path="messages" element={<MessagePage />} />
      </Route>
    </Routes>
  );
}