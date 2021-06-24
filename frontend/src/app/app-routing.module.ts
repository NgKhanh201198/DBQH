import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DangnhapComponent} from './pages/dangnhap/dangnhap.component';
import {TrangchuComponent} from './pages/trangchu/trangchu.component';
import {ChitietComponent} from './pages/chitiet/chitiet.component';
import {TiepnhanphananhComponent} from './pages/tiepnhanphananh/tiepnhanphananh.component';
import {AuthenticationGuard} from './core/guard/authentication.guard';
import {ERole} from './core/models/erole.enum';
import {AdminComponent} from './admin/admin.component';

const routes: Routes = [
    {
        path: 'dang-nhap',
        component: DangnhapComponent
    },
    {
        path: 'trang-chu',
        component: TrangchuComponent,
        canActivate: [AuthenticationGuard],
        data: {roles: [ERole.ADMIN, ERole.USER]}
    },
    {
        path: 'chi-tiet',
        component: ChitietComponent,
        canActivate: [AuthenticationGuard],
        data: {roles: [ERole.USER]}
    },
    {
        path: 'tiep-nhan-phan-anh',
        component: TiepnhanphananhComponent,
        canActivate: [AuthenticationGuard],
        data: {roles: [ERole.ADMIN, ERole.USER]}
    },
    {
        path: 'trang-quan-tri',
        loadChildren: () => import('src/app/admin/admin.module').then(module => module.AdminModule),
        canActivate: [AuthenticationGuard],
        data: {roles: [ERole.ADMIN]}
    },
    {
        path: '**',
        redirectTo: 'dang-nhap',
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
