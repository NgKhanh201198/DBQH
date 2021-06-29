import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AdminComponent} from './admin.component';
import {CreateAccoutComponent} from './account/create-accout/create-accout.component';
import {ListAccountComponent} from './account/list-account/list-account.component';

const routes: Routes = [
    {
        path: '',
        component: AdminComponent
    },
    {
        path: 'them-tai-khoan',
        component: CreateAccoutComponent
    },
    {
        path: 'danh-sach-tai-khoan',
        component: ListAccountComponent
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule {
}
