import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFornecedor, Fornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';
import { FornecedorComponent } from './fornecedor.component';
import { FornecedorDetailComponent } from './fornecedor-detail.component';
import { FornecedorUpdateComponent } from './fornecedor-update.component';

@Injectable({ providedIn: 'root' })
export class FornecedorResolve implements Resolve<IFornecedor> {
  constructor(private service: FornecedorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFornecedor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fornecedor: HttpResponse<Fornecedor>) => {
          if (fornecedor.body) {
            return of(fornecedor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Fornecedor());
  }
}

export const fornecedorRoute: Routes = [
  {
    path: '',
    component: FornecedorComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.fornecedor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FornecedorDetailComponent,
    resolve: {
      fornecedor: FornecedorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.fornecedor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FornecedorUpdateComponent,
    resolve: {
      fornecedor: FornecedorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.fornecedor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FornecedorUpdateComponent,
    resolve: {
      fornecedor: FornecedorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.fornecedor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
