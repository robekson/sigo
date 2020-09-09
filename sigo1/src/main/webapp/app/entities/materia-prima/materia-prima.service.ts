import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMateriaPrima } from 'app/shared/model/materia-prima.model';

type EntityResponseType = HttpResponse<IMateriaPrima>;
type EntityArrayResponseType = HttpResponse<IMateriaPrima[]>;

@Injectable({ providedIn: 'root' })
export class MateriaPrimaService {
  public resourceUrl = SERVER_API_URL + 'api/materia-primas';

  constructor(protected http: HttpClient) {}

  create(materiaPrima: IMateriaPrima): Observable<EntityResponseType> {
    return this.http.post<IMateriaPrima>(this.resourceUrl, materiaPrima, { observe: 'response' });
  }

  update(materiaPrima: IMateriaPrima): Observable<EntityResponseType> {
    return this.http.put<IMateriaPrima>(this.resourceUrl, materiaPrima, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMateriaPrima>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMateriaPrima[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
