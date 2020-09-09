import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompra, Compra } from 'app/shared/model/compra.model';
import { CompraService } from './compra.service';
import { CompraComponent } from './compra.component';
import { CompraDetailComponent } from './compra-detail.component';
import { CompraUpdateComponent } from './compra-update.component';

@Injectable({ providedIn: 'root' })
export class CompraResolve implements Resolve<ICompra> {
  constructor(private service: CompraService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompra> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((compra: HttpResponse<Compra>) => {
          if (compra.body) {
            return of(compra.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Compra());
  }
}

export const compraRoute: Routes = [
  {
    path: '',
    component: CompraComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.compra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompraDetailComponent,
    resolve: {
      compra: CompraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.compra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompraUpdateComponent,
    resolve: {
      compra: CompraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.compra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompraUpdateComponent,
    resolve: {
      compra: CompraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.compra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
