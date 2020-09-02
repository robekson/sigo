import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SigoTestModule } from '../../../test.module';
import { ForneceComponent } from 'app/entities/fornece/fornece.component';
import { ForneceService } from 'app/entities/fornece/fornece.service';
import { Fornece } from 'app/shared/model/fornece.model';

describe('Component Tests', () => {
  describe('Fornece Management Component', () => {
    let comp: ForneceComponent;
    let fixture: ComponentFixture<ForneceComponent>;
    let service: ForneceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [ForneceComponent],
      })
        .overrideTemplate(ForneceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ForneceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ForneceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Fornece(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.forneces && comp.forneces[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
