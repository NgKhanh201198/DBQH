import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AdminComponent} from './admin.component';
import {CreateAccoutComponent} from './account/create-accout/create-accout.component';

const routes: Routes = [
    {
        path: '',
        component: AdminComponent
    },
    {
        path: 'them-tai-khoan',
        component: CreateAccoutComponent
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule {
}
