import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminComponent} from './admin.component';
import {ListAccountComponent} from './account/list-account/list-account.component';
import {UpdateAccountComponent} from './account/update-account/update-account.component';
import {CreateAccountComponent} from './account/create-account/create-account.component';
import {CreateAgencyComponent} from './agency/create-agency/create-agency.component';
import {UpdateAgencyComponent} from './agency/update-agency/update-agency.component';
import {ListAgencyComponent} from './agency/list-agency/list-agency.component';

const routes: Routes = [
    {
        path: '',
        component: AdminComponent
    },
    {
        path: 'them-tai-khoan',
        component: CreateAccountComponent
    },
    {
        path: 'danh-sach-tai-khoan',
        component: ListAccountComponent
    },
    {
        path: 'danh-sach-tai-khoan/chinh-sua-thong-tin/:id',
        component: UpdateAccountComponent
    },
    {
        path: 'them-co-quan',
        component: CreateAgencyComponent
    },
    {
        path: 'danh-sach-co-quan',
        component: ListAgencyComponent
    },
    {
        path:'danh-sach-co-quan/chinh-sua-thong-tin/:id',
        component: UpdateAgencyComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule {
}
