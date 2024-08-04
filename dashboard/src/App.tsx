import './App.css'
import {Sidebar} from "@/feature/sidebar/Sidebar.tsx";
import {Topbar} from "@/feature/topbar/Topbar.tsx";
import {Settings} from "@/feature/settings/Settings.tsx";

export default function App() {
    return (
        <>
            <Settings></Settings>
            <div className="grid min-h-screen w-full md:grid-cols-[220px_1fr] lg:grid-cols-[280px_1fr]">
                <Sidebar></Sidebar>
                <div className="flex flex-col">
                    <Topbar></Topbar>
                    <main className="flex flex-1 flex-col gap-4 p-4 lg:gap-6 lg:p-6">
                    </main>
                </div>
            </div>
        </>
    )
}
