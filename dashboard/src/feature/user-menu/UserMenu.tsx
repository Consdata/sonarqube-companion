import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger
} from "@/ui-components/ui/dropdown-menu.tsx";
import {Button} from "@/ui-components/ui/button.tsx";
import {CircleUser} from "lucide-react";
import {useUserStore} from "@/feature/auth/auth-state.ts";
import {useNavigate} from "react-router-dom";
import {
    Dialog,
    DialogContent,
    DialogDescription, DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger
} from "@/ui-components/ui/dialog.tsx";
import {Label} from "@/ui-components/ui/label.tsx";
import {Input} from "@/ui-components/ui/input.tsx";

export function UserMenu() {

    const userState = useUserStore();
    const navigate = useNavigate();

    function logout() {
        userState.removeCredentials();
        navigate('/login');
    }

    return (
        <DropdownMenu>
            <DropdownMenuTrigger asChild>
                <Button variant="secondary" size="icon" className="rounded-full">
                    <CircleUser className="h-5 w-5"/>
                    <span className="sr-only">Toggle user menu</span>
                </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
                <DropdownMenuLabel>My Account</DropdownMenuLabel>
                <DropdownMenuSeparator/>
                <DropdownMenuItem>



                </DropdownMenuItem>
                <DropdownMenuItem>Support</DropdownMenuItem>
                <DropdownMenuSeparator/>
                <DropdownMenuItem onClick={logout}>Logout</DropdownMenuItem>
            </DropdownMenuContent>
        </DropdownMenu>
    );
}
