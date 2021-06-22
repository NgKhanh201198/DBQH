import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DangnhapComponent} from './pages/dangnhap/dangnhap.component';
import {TrangchuComponent} from './pages/trangchu/trangchu.component';
import {ChitietComponent} from './pages/chitiet/chitiet.component';
import {TiepnhanphananhComponent} from './pages/tiepnhanphananh/tiepnhanphananh.component';

const routes: Routes = [
    {
        path: 'dang-nhap',
        component: DangnhapComponent
    },
    {
        path: 'trang-chu',
        component: TrangchuComponent
    },
    {
        path: 'chi-tiet',
        component: ChitietComponent
    },
    {
        path: 'tiep-nhan-phan-anh',
        component: TiepnhanphananhComponent
    },
    {
        path: '**',
        redirectTo: 'trang-chu'
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
