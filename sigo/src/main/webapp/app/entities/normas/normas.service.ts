import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INormas } from 'app/shared/model/normas.model';

type EntityResponseType = HttpResponse<INormas>;
type EntityArrayResponseType = HttpResponse<INormas[]>;

@Injectable({ providedIn: 'root' })
export class NormasService {
  public resourceUrl = SERVER_API_URL + 'api/normas';

  constructor(protected http: HttpClient) {}

  create(normas: INormas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(normas);
    return this.http
      .post<INormas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(normas: INormas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(normas);
    return this.http
      .put<INormas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INormas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INormas[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(normas: INormas): INormas {
    const copy: INormas = Object.assign({}, normas, {
      date: normas.date && normas.date.isValid() ? normas.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((normas: INormas) => {
        normas.date = normas.date ? moment(normas.date) : undefined;
      });
    }
    return res;
  }
}
