import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AdminComponent} from './admin.component';
import {CreateAccoutComponent} from './account/create-accout/create-accout.component';
import {ListAccountComponent} from './account/list-account/list-account.component';
import {UpdateAccountComponent} from './account/update-account/update-account.component';

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
    {
        path: 'danh-sach-tai-khoan/chinh-sua-thong-tin/:id',
        component: UpdateAccountComponent
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule {
}
