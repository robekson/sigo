import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVenda } from 'app/shared/model/venda.model';

type EntityResponseType = HttpResponse<IVenda>;
type EntityArrayResponseType = HttpResponse<IVenda[]>;

@Injectable({ providedIn: 'root' })
export class VendaService {
  public resourceUrl = SERVER_API_URL + 'api/vendas';

  constructor(protected http: HttpClient) {}

  create(venda: IVenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(venda);
    return this.http
      .post<IVenda>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(venda: IVenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(venda);
    return this.http
      .put<IVenda>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVenda>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVenda[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(venda: IVenda): IVenda {
    const copy: IVenda = Object.assign({}, venda, {
      data: venda.data && venda.data.isValid() ? venda.data.format(DATE_FORMAT) : undefined,
      dataEntrega: venda.dataEntrega && venda.dataEntrega.isValid() ? venda.dataEntrega.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
      res.body.dataEntrega = res.body.dataEntrega ? moment(res.body.dataEntrega) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((venda: IVenda) => {
        venda.data = venda.data ? moment(venda.data) : undefined;
        venda.dataEntrega = venda.dataEntrega ? moment(venda.dataEntrega) : undefined;
      });
    }
    return res;
  }
}
