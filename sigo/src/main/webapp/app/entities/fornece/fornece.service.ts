import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFornece } from 'app/shared/model/fornece.model';

type EntityResponseType = HttpResponse<IFornece>;
type EntityArrayResponseType = HttpResponse<IFornece[]>;

@Injectable({ providedIn: 'root' })
export class ForneceService {
  public resourceUrl = SERVER_API_URL + 'api/forneces';

  constructor(protected http: HttpClient) {}

  create(fornece: IFornece): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fornece);
    return this.http
      .post<IFornece>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fornece: IFornece): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fornece);
    return this.http
      .put<IFornece>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFornece>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFornece[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fornece: IFornece): IFornece {
    const copy: IFornece = Object.assign({}, fornece, {
      data: fornece.data && fornece.data.isValid() ? fornece.data.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fornece: IFornece) => {
        fornece.data = fornece.data ? moment(fornece.data) : undefined;
      });
    }
    return res;
  }
}
