import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthenticationGuard} from './core/guard/authentication.guard';
import {ERole} from './core/models/erole.enum';
import {HomeComponent} from './pages/home/home.component';
import {DetailComponent} from './pages/detail/detail.component';
import {RecommendationsComponent} from './pages/recommendations/recommendations.component';
import {LoginComponent} from './pages/login/login.component';
import {AdminComponent} from './admin/admin.component';

const routes: Routes = [
    {
        path: 'dang-nhap',
        component: LoginComponent
    },
    {
        path: 'trang-chu',
        component: HomeComponent,
        canActivate: [AuthenticationGuard],
        data: {roles: [ERole.ADMIN, ERole.USER]}
    },
    {
        path: 'chi-tiet',
        component: DetailComponent,
        canActivate: [AuthenticationGuard],
        data: {roles: [ERole.ADMIN, ERole.USER]}
    },
    {
        path: 'tiep-nhan-phan-anh',
        component: RecommendationsComponent,
        canActivate: [AuthenticationGuard],
        data: {roles: [ERole.ADMIN, ERole.USER]}
    },
    {
        path: 'trang-quan-tri',
        loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
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
