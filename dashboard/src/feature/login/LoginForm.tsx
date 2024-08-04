import {Button} from "@/ui-components/ui/button"
import {Card, CardContent, CardDescription, CardHeader, CardTitle,} from "@/ui-components/ui/card"
import {Input} from "@/ui-components/ui/input"
import {Label} from "@/ui-components/ui/label"
import {api} from "@/api/api.ts";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {useUserStore} from "@/feature/auth/auth-state.ts";

export function LoginForm() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(false);

    const navigate = useNavigate();
    const userStore = useUserStore();

    function handle() {
        userStore.setCredentials(
            {
                username: username,
                password: password
            }
        );

        api.auth.login(username, password).then(response => {
            if (response.status === 200) {
                setError(false);
                navigate('/');
            }
        }, error => {
            if (error.response.status === 403 || error.response.status === 401) {
                setError(true);
            }
        });
    }

    return (<Card className="mx-auto max-w-sm">
        <CardHeader>
            <CardTitle className="text-2xl">Login</CardTitle>
            <CardDescription>
                Enter your email below to login to your account
            </CardDescription>
        </CardHeader>
        <CardContent>
            <div className="grid gap-4">
                <div className="grid gap-2">
                    <Label htmlFor="email">Email</Label>
                    <Input
                        id="email"
                        type="email"
                        placeholder="m@example.com"
                        required
                        value={username}
                        onChange={ev => setUsername(ev.target.value)}
                    />
                </div>
                <div className="grid gap-2">
                    <div className="flex items-center">
                        <Label htmlFor="password">Password</Label>
                    </div>
                    <Input id="password" type="password" required value={password}
                           onChange={ev => setPassword(ev.target.value)}/>
                </div>
                <Button type="submit" className="w-full" onClick={handle}>
                    Login
                </Button>
            </div>
            {(() => {
                if (error) {
                    return <div>Error</div>;
                }
            })()}

        </CardContent>
    </Card>);
}
