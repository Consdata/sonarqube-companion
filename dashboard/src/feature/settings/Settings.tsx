import {Dialog, DialogContent} from "@/ui-components/ui/dialog.tsx";
import {Button} from "@/ui-components/ui/button.tsx";
import {Input} from "@/ui-components/ui/input.tsx";
import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/ui-components/ui/card.tsx";
import {Link} from "react-router-dom";
import {Checkbox} from "@/ui-components/ui/checkbox.tsx";
import {ScrollArea} from "@/ui-components/ui/scroll-area.tsx";

export function Settings() {
    return (
        <Dialog open={false}>
            <DialogContent className="sm:max-w-[90%] sm:max-h-[90%]">
                <main
                    className="flex flex-1 flex-col gap-4 w-full">
                    <div className="mx-auto grid w-full gap-2">
                        <h1 className="text-3xl font-semibold">Settings</h1>
                    </div>
                    <div
                        className="mx-auto grid w-full max-h-[90%] items-start gap-6 md:grid-cols-[180px_1fr] lg:grid-cols-[250px_1fr]">
                        <nav
                            className="grid gap-4 text-sm text-muted-foreground" x-chunk="dashboard-04-chunk-0"
                        >
                            <Link href="#" className="font-semibold text-primary">
                                General
                            </Link>
                            <Link href="#">Security</Link>
                            <Link href="#">Integrations</Link>
                            <Link href="#">Support</Link>
                            <Link href="#">Organizations</Link>
                            <Link href="#">Advanced</Link>
                        </nav>
                        <div className="overflow-hidden">
                            <ScrollArea>
                                <div className="grid gap-6 max-h-[90%]">
                                        <Card x-chunk="dashboard-04-chunk-1">
                                            <CardHeader>
                                                <CardTitle>Store Name</CardTitle>
                                                <CardDescription>
                                                    Used to identify your store in the marketplace.
                                                </CardDescription>
                                            </CardHeader>
                                            <CardContent>
                                                <form>
                                                    <Input placeholder="Store Name"/>
                                                </form>
                                            </CardContent>
                                            <CardFooter className="border-t px-6 py-4">
                                                <Button>Save</Button>
                                            </CardFooter>
                                        </Card>
                                        <Card x-chunk="dashboard-04-chunk-2">
                                            <CardHeader>
                                                <CardTitle>Plugins Directory</CardTitle>
                                                <CardDescription>
                                                    The directory within your project, in which your plugins are
                                                    located.
                                                </CardDescription>
                                            </CardHeader>
                                            <CardContent>
                                                <form className="flex flex-col gap-4">
                                                    <Input
                                                        placeholder="Project Name"
                                                        defaultValue="/content/plugins"
                                                    />
                                                    <div className="flex items-center space-x-2">
                                                        <Checkbox id="include" defaultChecked/>
                                                        <label
                                                            htmlFor="include"
                                                            className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                                        >
                                                            Allow administrators to change the directory.
                                                        </label>
                                                    </div>
                                                </form>
                                            </CardContent>
                                            <CardFooter className="border-t px-6 py-4">
                                                <Button>Save</Button>
                                            </CardFooter>
                                        </Card>
                                        <Card x-chunk="dashboard-04-chunk-2">
                                            <CardHeader>
                                                <CardTitle>Plugins Directory</CardTitle>
                                                <CardDescription>
                                                    The directory within your project, in which your plugins are
                                                    located.
                                                </CardDescription>
                                            </CardHeader>
                                            <CardContent>
                                                <form className="flex flex-col gap-4">
                                                    <Input
                                                        placeholder="Project Name"
                                                        defaultValue="/content/plugins"
                                                    />
                                                    <div className="flex items-center space-x-2">
                                                        <Checkbox id="include" defaultChecked/>
                                                        <label
                                                            htmlFor="include"
                                                            className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                                        >
                                                            Allow administrators to change the directory.
                                                        </label>
                                                    </div>
                                                </form>
                                            </CardContent>
                                            <CardFooter className="border-t px-6 py-4">
                                                <Button>Save</Button>
                                            </CardFooter>
                                        </Card>
                                    </div>
                            </ScrollArea>
                        </div>
                    </div>
                </main>
            </DialogContent>
        </Dialog>
    );
}
